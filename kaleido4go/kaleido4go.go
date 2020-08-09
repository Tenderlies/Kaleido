package main

import (
	"crypto/sha256"
	"fmt"
	"github.com/andlabs/ui"
	_ "github.com/andlabs/ui/winmanifest"
	_ "github.com/mattn/go-sqlite3"
	proto "google.golang.org/genproto/googleapis/devtools/remoteexecution/v1test"
)

func proxyList() ui.Control {

	return nil
}

type ruleTableModelHandler struct {
	staticRule []string;
}

func (mh *ruleTableModelHandler) ColumnTypes(m *ui.TableModel) []ui.TableValue {
	return []ui.TableValue{ui.TableString("")}
}

func (mh *ruleTableModelHandler) NumRows(m *ui.TableModel) int {
	return 15
}

func (mh *ruleTableModelHandler) CellValue(m *ui.TableModel, row, column int) ui.TableValue {
	if column == 0 {
		return ui.TableString("哈哈")
	}
	return nil
}

func (mh *ruleTableModelHandler) SetCellValue(m *ui.TableModel, row, column int, value ui.TableValue) {

}

var rtmh ruleTableModelHandler

func proxyRule() ui.Control {

	tab := ui.NewVerticalBox()

	grid := ui.NewGrid();
	addBtn := ui.NewButton("✚")
	delBtn := ui.NewButton("✖")
	refreshBtn := ui.NewButton("♝")
	grid.Append(addBtn, 0, 0, 1, 1, false, ui.AlignFill, false, ui.AlignFill)
	grid.Append(delBtn, 1, 0, 1, 1, false, ui.AlignFill, false, ui.AlignFill)
	grid.Append(refreshBtn, 2, 0, 1, 1, true, ui.AlignEnd, false, ui.AlignFill)
	tab.Append(grid, false)
	tab.Append(ui.NewHorizontalSeparator(), false)

	staticRule := ui.NewLabel("代理列表")
	tab.Append(staticRule, false)

	m := ui.NewTableModel(&rtmh)

	table := ui.NewTable(&ui.TableParams{
		Model:                         m,
		RowBackgroundColorModelColumn: 3,
	})

	table.AppendTextColumn("", 0, ui.TableModelColumnNeverEditable, nil)

	tab.Append(table, true)

	return tab
}

func proxyMode() ui.Control {

	return nil
}

func panel() {
	mainwin := ui.NewWindow("SSR Client", 400, 480, false)
	mainwin.SetMargined(true)
	mainwin.OnClosing(func(*ui.Window) bool {
		mainwin.Destroy()
		ui.Quit()
		return false
	})
	ui.OnShouldQuit(func() bool {
		mainwin.Destroy()
		return true
	})

	vbox := ui.NewVerticalBox()
	vbox.SetPadded(true)
	mainwin.SetChild(vbox)

	switchBtn := ui.NewButton("连接代理")
	vbox.Append(switchBtn, false)

	vbox.Append(ui.NewHorizontalSeparator(), false)
	tab := ui.NewTab()

	tab.Append("代理地址", proxyList())
	tab.Append("代理规则", proxyRule())
	tab.Append("代理模式", proxyMode())

	vbox.Append(tab, true)
	mainwin.Show()
}

func main() {
	//ui.Main(panel)
	var action proto.Action

	action.Reset()
	var platform proto.Platform
	var prop proto.Platform_Property
	prop.Name = "ARCH"
	prop.Value = "x86"
	platform.Properties = append(platform.Properties, &prop)
	action.Platform = &platform
	action.XXX_DiscardUnknown()
	action.XXX_Merge(nil)
	action.ProtoMessage()
	data, _ := action.XXX_Marshal(nil, true)
	actionKey := sha256.Sum256(data)
	fmt.Println(string(data));
	fmt.Println(fmt.Sprintf("%x", actionKey));
	data1, _ := action.XXX_Marshal(nil, true)
	actionKey1 := sha256.Sum256(data1)
	fmt.Println(fmt.Sprintf("%x", actionKey1));
	data2, _ := action.XXX_Marshal(nil, true)
	fmt.Println(string(data2));
	actionKey2 := sha256.Sum256(data2)
	fmt.Println(fmt.Sprintf("%x", actionKey2));
}
