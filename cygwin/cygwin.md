# cygwin

## 配置 mirror

`http://mirrors.163.com`

## 安装常用软件

```text
automake、curl、gcc-core、git、gcc-g++、inetutils、lynx、make、mingw-gcc、ming-g++、patch、pip、python、vim、wget、zsh
```

`alias vi="vim"`

## 安装 apt-cyg

```shell
lynx -source rawgit.com/transcode-open/apt-cyg/master/apt-cyg > apt-cyg

install apt-cyg /usr/bin

apt-cyg mirror http://mirrors.163.com/cygwin
```

`alias apt="apt-cyg"`


## 安装 netcat

```
tar xvf netcat-0.7.1.tar.gz
patch -p0 < netcat-cygwin.patch
cd netcat-0.7.1
./configure --build=i686
make
make install 
```

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
Locale=zh_CN
Charset=UTF-8
Columns=120
Rows=35
Language=zh_CN
WindowShortcuts=no
SwitchShortcuts=no
ZoomShortcuts=no
AltFnShortcuts=no
ClickTargetMod=off
CtrlAltIsAltGr=yes
ComposeKey=ctrl
CopyAsHTML=2
MiddleClickAction=paste
BackspaceSendsBS=yes
DeleteSendsDEL=no
AltGrIsAlsoAlt=no
BoldAsFont=no
AllowSetSelection=yes
EmojiPlacement=align
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

```shell 可选
git config --global http.proxy http://127.0.0.1:1081
git config --global http.sslVerify false
```

```shell
git config --global core.autocrlf true
git config --global core.autolf false
git config --global core.safecrlf false
git config --global credential.helper store
git config --global --add oh-my-zsh.hide-dirty 1
git config --global --add oh-my-zsh.hide-status 0
```
