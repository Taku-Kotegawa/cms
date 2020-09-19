<section class="content-header">
    <div class="container">
        <div class="row mb-2">
            <div class="col-18">
                <!-- ページタイトルを記入 -->
                <h3></h3>
            </div>
            <div class="col-18">
                <!-- ページタイトル右の余白 -->
            </div>
        </div>
    </div>
</section>
<section class="content">
    <div class="container">
        <t:messagesPanel panelClassName="callout" panelTypeClassPrefix="callout-" disableHtmlEscape="true" />
        <!-- ここより下にメインコンテンツを記入 -->

        <div class="callout callout-success">
            事前登録のメールアドレスにパスワード再発行用URLを送信しました。
            URLにアクセスし、下記の暗証番号を入力してください。(暗証番号には期限があります)
            <br>
            <h3>暗証番号 : <span id=secret>${f:h(secret)}</span></h3>
        </div>

        <!-- ここより上にメインコンテンツを記入 -->
    </div>
</section>
