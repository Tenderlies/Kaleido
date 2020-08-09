package action

import (
	"../arg"
	"crypto/sha256"
	"fmt"
	"github.com/pkg/errors"
	proto "google.golang.org/genproto/googleapis/devtools/remoteexecution/v1test"
	"io"
	"os"
	"runtime"
	"sort"
)

type CWAction struct {
	action       *proto.Action
	actionKey    *string
	actionResult *proto.ActionResult
}

var actionHandler *CWAction

func GetAction(args *arg.Args) (*proto.Action, error) {
	if (actionHandler.action != nil) {
		return actionHandler.action, nil
	}
	var action proto.Action
	inputRootDigest, err := getInputRootDigest(args.GetInputs())
	if (err != nil) {
		return nil, err;
	}
	action.InputRootDigest = inputRootDigest
	action.CommandDigest = getCmdDigest(args.GetCommand())
	action.OutputFiles = *args.GetOutputs()
	action.Platform = getPlatForm()

	actionHandler.action = &action

	return actionHandler.action, nil
}

func GetActionKey() (*string, error) {
	if (actionHandler.actionKey != nil) {
		return actionHandler.actionKey, nil
	}

	if (actionHandler.action == nil) {
		return nil, errors.New("Action not initialized")
	}

	actionKey := fmt.Sprintf("%x", sha256.Sum256([]byte(actionHandler.action.String())))
	actionHandler.actionKey = &actionKey
	return actionHandler.actionKey, nil
}

func getInputRootDigest(inputs *[]string) (*proto.Digest, error) {
	var directory proto.Directory
	fileNodes := make([]*proto.FileNode, 0)
	for _, input := range *inputs {
		var node proto.FileNode
		node.Name = input
		var digest proto.Digest
		file, err := os.Open("file.txt")
		if err != nil {
			return nil, err
		}
		defer file.Close()
		sha := sha256.New()
		if _, err := io.Copy(sha, file); err != nil {
			return nil, err
		}
		digest.Hash = fmt.Sprintf("%x", sha.Sum(nil))

		node.Digest = &digest

		fileInfo, err := os.Stat(input)
		if (err != nil) {
			return nil, err;
		}
		node.IsExecutable = uint32(fileInfo.Mode().Perm()&os.FileMode(64)) == uint32(64)

		fileNodes = append(fileNodes, &node)
	}

	sort.SliceStable(fileNodes, func(i, j int) bool {
		return fileNodes[i].Name > fileNodes[j].Name
	})

	directory.Files = fileNodes
	var digest proto.Digest
	digest.Hash = fmt.Sprintf("%x", sha256.Sum256([]byte(directory.String())))

	return &digest, nil;
}

func getCmdDigest(cmd *string) *proto.Digest {
	cmdHash := sha256.Sum256([]byte(*cmd))
	hashStr := fmt.Sprintf("%x", cmdHash)
	var digest proto.Digest
	digest.Hash = hashStr
	return &digest
}

func getPlatForm() *proto.Platform {
	var platform proto.Platform

	properties := make([]*proto.Platform_Property, 0)

	var platFormOS proto.Platform_Property
	platFormOS.Name = "OS"
	platFormOS.Value = runtime.GOOS
	properties = append(properties, &platFormOS)

	var platFormArch proto.Platform_Property
	platFormArch.Name = "ARCH"
	platFormArch.Value = runtime.GOARCH
	properties = append(properties, &platFormArch)

	return &platform
}
