# プロジェクト構成

このプロジェクトは、学習進捗管理アプリケーション「StudyTracker」のソースコードです。


## インストール方法（環境構築手順）

〜りつよろしく〜


## 開発手順ガイド

1. リポジトリをクローン
プロジェクトのリポジトリをローカル環境にコピーします。
git clone git@github.com:masyuuuu31/studyTracker.git

リポジトリをクローンした後、作業ディレクトリに移動します
cd studyTracker

2. 新しいブランチを作成

git checkout -b feature/add-login

3. コードを編集し、コミット
変更内容をステージングしてコミットします。
git add .
git commit -m "Add: ログイン機能の追加"

"コミットメッセージのルール"
変更内容 + @氏名で統一
例）resetting.htmlを作成@nishizawa 

4. PullRequestをりつ宛に送信
作成したブランチをリモートリポジトリにプッシュします
git push origin <ブランチ名>

GitHubでPull Requestを作成します。
PRを作成後、りつ宛てに指定し、レビューを依頼してください。

5. Discordの「git連絡用」で連絡
PRを作成したら、Discordの「git連絡用」チャンネルで通知を行います。以下のテンプレートを参考にしてください

<例>
PRを作成しました：
- タイトル：<PRのタイトル>
- URL：<PRのURL>
ご確認をお願いします。

6. Merge完了後、今回作成したブランチ/リモートブランチを削除
PRが承認され、マージされた場合、以下の手順を実行してください

ローカルのブランチを削除
git branch -d <ブランチ名>

リモートのブランチを削除
git push origin --delete <ブランチ名>

ローカルのmasterブランチを更新
git checkout master
git pull origin master

7. 注意事項
コンフリクトの解消
マージ時にコンフリクトが発生した場合、以下の手順で解消してください：

最新のmasterを取得
git fetch origin
git merge origin/master

必要に応じてコードを修正し、再度コミット
git add .
git commit -m "Fix: コンフリクト解消"

修正後に再度プッシュ
git push origin <ブランチ名>

8. 図解：開発フロー

master (最新の状態)
   |
   ├── feature/新機能ブランチ
   |      ├── コード編集
   |      ├── コミット
   |      └── Pull Request
   |
   └── PRマージ → master更新 → ブランチ削除


## ディレクトリ構成一覧

studyTracker
├── mvnw                      # Maven Wrapperスクリプト（UNIX/Linux用）
├── mvnw.cmd                  # Maven Wrapperスクリプト（Windows用）
├── pom.xml                   # Mavenの設定ファイル（依存関係やビルド設定を管理）
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── studytracker
│   │   │               ├── StudytrackerApplication.java      # Spring Bootアプリケーションのエントリーポイント
│   │   │               ├── auth                              # 認証・認可機能関連のパッケージ
│   │   │               │   ├── AuthController.java           # 認証リクエストを処理するコントローラー
│   │   │               │   ├── CustomUserDetails.java        # Spring Security用のユーザー詳細クラス
│   │   │               │   ├── CustomUserDetailsService.java # ユーザー認証サービスの実装
│   │   │               │   └── SecurityConfig.java           # セキュリティ設定クラス
│   │   │               ├── entity                        # データベースエンティティを定義するパッケージ
│   │   │               │   ├── MUser.java                # ユーザー情報のエンティティ
│   │   │               │   ├── MainTask.java             # メインタスクのエンティティ
│   │   │               │   └── SubTask.java              # サブタスクのエンティティ
│   │   │               ├── form                          # フォームデータを管理するパッケージ
│   │   │               │   └── UserRegistrationForm.java # ユーザー登録用フォームデータモデル
│   │   │               ├── repository                    # リポジトリクラスを格納
│   │   │               │   ├── MainTaskRepository.java   # メインタスクのリポジトリ
│   │   │               │   ├── SubTaskRepository.java    # サブタスクのリポジトリ
│   │   │               │   └── UserRepository.java       # ユーザー情報のリポジトリ
│   │   │               └── service                       # ビジネスロジックを実装するパッケージ
│   │   │                   └── UserService.java          # ユーザー関連のサービスクラス
│   │   └── resources
│   │       ├── application.properties                    # アプリケーションの設定ファイル
│   │       ├── static                                    # 静的リソース（CSSやJavaScript）
│   │       │   ├── css
│   │       │   │   └── stylesheet.css                   # アプリケーションのスタイル設定
│   │       │   └── js                                   # （将来的にJavaScriptを配置）
│   │       └── templates                                # HTMLテンプレートを格納
│   │           ├── hello.html                           # サンプルページ
│   │           ├── login.html                           # ログインページ
│   │           ├── resetting.html                       # パスワードリセットページ
│   │           └── signup.html                          # ユーザー登録ページ
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── studytracker
│                       └── StudytrackerApplicationTests.java # アプリケーションのテスト
└── target                                               # Mavenビルド後の成果物
    ├── classes                                          # コンパイル済みのアプリケーションコード
    │   ├── application.properties                       # ビルド後の設定ファイル
    │   ├── com
    │   │   └── example
    │   │       └── studytracker
    │   │           ├── StudytrackerApplication.class   # エントリーポイントのクラスファイル
    │   │           ├── auth                            # 認証関連のクラスファイル
    │   │           ├── entity                          # エンティティクラスのコンパイル済みファイル
    │   │           ├── form                            # フォームモデルのクラスファイル
    │   │           ├── repository                      # リポジトリのクラスファイル
    │   │           └── service                         # サービスクラスのクラスファイル
    │   ├── static                                      # ビルド後の静的リソース
    │   └── templates                                   # ビルド後のテンプレート
    └── test-classes                                    # テストコードのコンパイル済みクラス

