# SpeechRecognizerSample

## 概要
AndroidのSpeechRecognizerを使用してオフライン音声認識(Speech-To-Text)を行うサンプルプロジェクトです。  

AndroidManifest.xmlの
```xml :AndroidManifest.xml
<uses-permission android:name="android.permission.INTERNET" />
```
を有効にして
MainActivity.ktの
```kotlin MainActivity.kt
speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true)
```
をコメントアウトすればオンラインでの音声認識も可能です

## 環境
IDE:Android Studio 3.4.1  
言語:Kotlin  
動作確認:Android 6.0/9.0  

## 前準備
オフラインの音声認識メニューのすべてのタブから日本語を選択してインストールしてください。
これが無いとオフライン音声認識が失敗します。


[Android 6.0]  
設定 -> 言語と入力 -> Google音声入力 -> オフラインの音声認識

<img src="https://github.com/cryptocat-miner/SpeechRecognizerSample/blob/master/images/offline_settings1.png" width="320px" alt="オフライン音声認識設定1">
  

<img src="https://github.com/cryptocat-miner/SpeechRecognizerSample/blob/master/images/offline_settings2.png" width="320px" alt="オフライン音声認識設定2">
  
<img src="https://github.com/cryptocat-miner/SpeechRecognizerSample/blob/master/images/offline_settings3.png" width="320px" alt="オフライン音声認識設定3">

[Android 9.0]  
設定 -> システム ->　言語と入力 -> 仮想キーボード -> Google音声入力 -> オフラインの音声認識

<img src="https://github.com/cryptocat-miner/SpeechRecognizerSample/blob/master/images/offline_settings_android9.0_1.png" width="320px" alt="オフライン音声認識(Android9.0 1)">

<img src="https://github.com/cryptocat-miner/SpeechRecognizerSample/blob/master/images/offline_settings_android9.0_2.png" width="320px" alt="オフライン音声認識(Android9.0 2)">

<img src="https://github.com/cryptocat-miner/SpeechRecognizerSample/blob/master/images/offline_settings_android9.0_3.png" width="320px" alt="オフライン音声認識(Android9.0 3)">

<img src="https://github.com/cryptocat-miner/SpeechRecognizerSample/blob/master/images/offline_settings_android9.0_4.png" width="320px" alt="オフライン音声認識(Android9.0 4)">

<img src="https://github.com/cryptocat-miner/SpeechRecognizerSample/blob/master/images/offline_settings_android9.0_5.png" width="320px" alt="オフライン音声認識(Android9.0 5)">



## 説明
最初にマイクのパーミッションリクエストで許可された後、継続的に音声認識を行いテキストを表示します。  
Androidのライフサイクルでの処理は無視しています。  
音声認識の結果を出力する毎にピロン、と効果音が鳴るのでうるさかったら音を小さくしてください。

## ライセンス
[Unlicense](https://github.com/cryptocat-miner/SpeechRecognizerSample/blob/master/LICENSE)

