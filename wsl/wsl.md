# WSL

## 更改默认命令及用户

```shell
wsl -s ubuntu-18.04
wsl config --default-user root
```

## 配置镜像

`/etc/apt/sources.list`

1.`bionic`

  ```list
deb http://mirrors.aliyun.com/ubuntu/ bionic main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ bionic main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ bionic-security main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ bionic-security main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ bionic-updates main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ bionic-updates main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ bionic-proposed main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ bionic-proposed main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ bionic-backports main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ bionic-backports main restricted universe multiverse
```

2.`focal`

  ```list
deb http://mirrors.aliyun.com/ubuntu/ focal main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ focal main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ focal-security main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ focal-security main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ focal-updates main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ focal-updates main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ focal-proposed main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ focal-proposed main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ focal-backports main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ focal-backports main restricted universe multiverse
```

## 配置 Git

```shell
git config --global core.autocrlf true
git config --global core.autolf true
git config --global core.safecrlf true
git config --global credential.helper store
git config --global user.email ""
git config --global user.name ""
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
wget https://raw.githubusercontent.com/caiogondim/bullet-train-oh-my-zsh-theme/master/bullet-train.zsh-theme -O $ZSH_CUSTOM/themes/bullet-train.zsh-theme
git clone https://github.com/zsh-users/zsh-autosuggestions ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-autosuggestions
```

## 配置 ~/.zshrc

```text
ZSH_THEME="bullet-train"

export WIN=$(cat /etc/resolv.conf | grep nameserver | awk '{print $2;}')
export DISPLAY=$WIN:0.0

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

### WSL2

```bat
C:\\WINDOWS\\System32\\wsl.exe
```

### WSL1

`-t zsh`

```shell
set "PATH=%ConEmuBaseDirShort%\wsl;%PATH%" & %ConEmuBaseDirShort%\conemu-cyg-64.exe --wsl -cur_console:pm:/mnt -t zsh
```

## WSL1 桌面安装

### 安装 xfce 桌面

```shell
sudo apt update && sudo apt -y upgrade
sudo apt install xfce4 xfce4-terminal
```

### 配置 start-ubuntu-xfce-desktop.bat

```bat
start /B x410.exe /desktop
ubuntu1804 run "if [ -z \"$(pidof xfce4-session)\" ]; then export DISPLAY=127.0.0.1:0.0; xfce4-session; pkill '(gpg|ssh)-agent'; fi;"
```

### 配置 x140.vbs

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

### 配置 wslu 快捷方式

```bat
C:\Windows\System32\wscript.exe "D:\scripts\wsl\x140.vbs" "start-ubuntu-xfce-desktop"
```
