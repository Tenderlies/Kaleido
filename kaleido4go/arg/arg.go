package arg

import "os"

type Args struct {
	inputs  []string
	outputs []string
	rule    string
	command string
}

func (a *Args) GetInputs() *[]string {
	return &a.inputs;
}

func (a *Args) GetOutputs() *[]string {
	return &a.outputs;
}

func (a *Args) GetRule() *string {
	return &a.rule
}

func (a *Args) GetCommand() *string {
	return &a.command
}

var args *Args

func GetArgs() *Args {
	if (args != nil) {
		return args;
	}

	argsArr := os.Args
	var argsObj Args
	status := 0;
	for _, arg := range argsArr {
		switch arg {
		case "--in":
			status = 1
			break
		case "--out":
			status = 2
			break
		case "--command":
			status = 3
			break
		case "--rule":
			status = 4
			break
		default:
			switch status {
			case 1:
				argsObj.inputs = append(argsObj.inputs, arg)
				break
			case 2:
				argsObj.outputs = append(argsObj.outputs, arg)
				break
			case 3:
				argsObj.command = arg
			case 4:
				argsObj.rule = arg
			default:
				break
			}
		}
	}
	args = &argsObj
	return args;
}
