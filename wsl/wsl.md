# WSL

## 更改默认用户

```shell
ubuntu1804.exe config --default-user root
```

## 安装 powerline 字体

```shell
git clone https://github.com/powerline/fonts.git
```

Run install.ps1 with powershell

## 安装 ZSH 套件

```shell
apt install -y zsh
sh -c "$(wget -O- https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"
wget https://raw.githubusercontent.com/caiogondim/bullet-train-oh-my-zsh-theme/master/bullet-train.zsh-theme -o $ZSH_CUSTOM/themes/bullet-train.zsh-theme
git clone https://github.com/zsh-users/zsh-autosuggestions ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-autosuggestions
```

## 配置 ~/.zshrc

```text
ZSH_THEME="bullet-train"

BULLETTRAIN_CONTEXT_BG=cyan
BULLETTRAIN_STATUS_EXIT_SHOW=true
BULLETTRAIN_CONTEXT_HOSTNAME=TS

BULLETTRAIN_PROMPT_ORDER=(
        context
        time
        dir
        git
        status
        cmd_exec_time
        )

plugins=(
  git
  z
  zsh-autosuggestions
  )
```

## 配置 Cmder WSL::zsh

```shell
set "PATH=%ConEmuBaseDirShort%\wsl;%PATH%" & %ConEmuBaseDirShort%\conemu-cyg-64.exe --wsl -cur_console:pm:/mnt -t zsh
```

`-t zsh`

## 安装 xfce 桌面

```shell
sudo apt update && sudo apt -y upgrade
sudo apt install xfce4 xfce4-terminal
```

## 配置 start-ubuntu-xfce-desktop.bat

```bat
start /B x410.exe /desktop
ubuntu1804 run "if [ -z \"$(pidof xfce4-session)\" ]; then export DISPLAY=127.0.0.1:0.0; xfce4-session; pkill '(gpg|ssh)-agent'; fi;"
```

## 配置 x140.vbs

```vbs
If WScript.Arguments.Count <= 0 Then
    WScript.Quit
End If

bat = Left(WScript.ScriptFullName, InStrRev(WScript.ScriptFullName, "\")) & WScript.Arguments(0) & ".bat"
arg = ""

If WScript.Arguments.Count > 1 Then
    arg = WScript.Arguments(1)
End If

CreateObject("WScript.Shell").Run """" & bat & """ """ & arg & """", 0, False
```

## 配置 wslu 快捷方式

```bat
C:\Windows\System32\wscript.exe "D:\scripts\wsl\x140.vbs" "start-ubuntu-xfce-desktop"
```
