
## BezierSeekBar
> 优雅的区间选择器，贝塞尔曲线样式，丰富的自定义内容，简单易懂的上手方式，

[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg)](https://android-arsenal.com/api?level=19) 
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Download](https://img.shields.io/badge/Download-1.0.4-green.svg) ](https://github.com/fairytale110/BezierSeekBar/archive/1.0.1.zip)

### 预览

![preview.gif](https://github.com/fairytale110/BezierSeekBar/raw/master/release/preview_1.0.1.gif)

### 演示 DEMO

 [![Download Demo APK](https://img.shields.io/badge/Download%20APK-1.0.4-green.svg) ](https://github.com/fairytale110/BezierSeekBar/raw/master/release/app-release.apk) 

或者直接手机扫码下载：

![APK 1.0.1](https://github.com/fairytale110/BezierSeekBar/raw/master/release/demo_1.0.1_qrcode.png)

### 功能


- [x] 超级简单的使用方式。

- [x] 所有内容可自定义颜色
- [x] 顺滑的交互动画
- [x] 自由配置样式
- [x] 选择区间可控、实时监听选择结果。

### 怎么使用？ 

仓库引用:

步骤 1. 添加 JitPack 依赖 到你的 build.gradle:
```
  allprojects {
     repositories {
       ...
       maven { url 'https://jitpack.io' }
     }
  }
```
步骤 2. 添加仓库
```
	dependencies {
	        implementation 'com.github.fairytale110:BezierSeekBar:1.0.4'
	}
```

### 使用方式

```java
  <tech.nicesky.bezierseekbar.BezierSeekBar
        android:id="@+id/bsBar_test"
        app:bsBar_color_ball="@android:color/white"
        app:bsBar_color_bg_selected="@android:color/white"
        app:bsBar_color_line="@android:color/white"
        app:bsBar_color_value="@android:color/white"
        app:bsBar_color_value_selected="#ef5350"
        app:bsBar_value_min="30"
        app:bsBar_value_max="120"
        app:bsBar_value_selected="65"
        app:bsBar_unit="kg"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
	app:bsBar_down_text_margin_top="11dp"
        app:bsBar_down_text_size="14sp"
        app:bsBar_select_text_size="20sp"
        app:bsBar_ball_circle_radius="4dp"
        app:bsBar_line_width="1dp"
        />
```
或者 
```java

   private void createBsbar() {
        fram.setBackgroundColor(Color.WHITE);
        
        BezierSeekBar seekBar = new BezierSeekBar(this);
        seekBar.setColorBall(Color.BLACK);
        seekBar.setColorLine(Color.BLACK);
        seekBar.setColorValueSelected(Color.WHITE);
        seekBar.setColorValue(Color.BLACK);
        seekBar.setColorBgSelected(Color.BLACK);
        seekBar.setValueMax(150);
        seekBar.setValueMin(20);
        seekBar.setValueSelected(60);
        seekBar.setUnit("mm");
        seekBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,     ViewGroup.LayoutParams.WRAP_CONTENT));
        seekBar.setSelectedListener(new OnSelectedListener() {
            @Override
            public void onSelected(int value) {
                checkLength(value);
            }
        });
        fram.addView(seekBar);
        //checkLength(seekBar.getValueSelected());
    }
```

### 源码解析

https://juejin.im/post/5bebed2fe51d450df867c3c1

### 参与贡献
fairytale110@foxmail.com


### 作者
fairytale110@foxmail.com
> 简书: http://jianshu.com/u/d95b27ffdd3c

> 掘金: https://juejin.im/user/596d91ee6fb9a06bb874a800/pins


### 开源协议

```
  Copyright 2018 fairytale110

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```

### Github :https://github.com/fairytale110/BezierSeekBar
