# cygwin

## 配置 mirror

`http://mirrors.163.com`

## 安装常用软件

```text
curl、git、lynx、make、mingw-gcc、ming-g++、pip、python、vim、wget、zsh
```

`alias vi="vim"`

## 安装 apt-cyg

```shell
lynx -source rawgit.com/transcode-open/apt-cyg/master/apt-cyg > apt-cyg

install apt-cyg /usr/bin

apt-cyg mirror http://mirrors.163.com/cygwin
```

`alias apt="apt-cyg"`

## 配置 vim

```shell
cat > ~/.vimrc <<"EOF"
set nohlsearch
EOF
```

## 配置 mintty

```shell
cat > ~/.minttyrc <<"EOF"
ThemeFile=xterm
Term=xterm-256color
Font=DejaVu Sans Mono for Powerline
FontHeight=10
BoldAsColour=no
Locale=C
Charset=UTF-8
Columns=120
Rows=35
Language=zh_CN
WindowShortcuts=no
SwitchShortcuts=no
ZoomShortcuts=no
AltFnShortcuts=no
MiddleClickAction=paste
BackspaceSendsBS=yes
DeleteSendsDEL=yes
AltGrIsAlsoAlt=no
BackgroundColour=13,25,38
ForegroundColour=217,230,242
CursorColour=217,230,242
Black=0,0,0
BoldBlack=38,38,38
Red=184,122,122
BoldRed=219,189,189
Green=122,184,122
BoldGreen=189,219,189
Yellow=184,184,122
BoldYellow=219,219,189
Blue=122,122,184
BoldBlue=189,189,219
Magenta=184,122,184
BoldMagenta=219,189,219
Cyan=122,184,184
BoldCyan=189,219,219
White=217,217,217
BoldWhite=255,255,255
EOF
```

## 配置 zsh

```shell
sh -c "$(wget -O- https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"

wget https://raw.githubusercontent.com/caiogondim/bullet-train-oh-my-zsh-theme/master/bullet-train.zsh-theme -O $ZSH_CUSTOM/themes/bullet-train.zsh-theme

git clone https://github.com/zsh-users/zsh-autosuggestions ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-autosuggestions
```

## 配置 git

```shell
git config --global core.autocrlf true
git config --global core.autolf true
git config --global core.safecrlf true
git config --global credential.helper store
```
