<!-- Navbar -->
<nav class="main-header navbar navbar-expand navbar-dark navbar-light">

    <!-- ログインしていない状態で表示するメニュー -->
    <sec:authorize access="!isAuthenticated()">
        <ul class="navbar-nav">
            <li class="nav-item d-none d-sm-inline-block">
                <a href="${f:h(pageContext.request.contextPath)}/" class="nav-link">SystemName</a>
            </li>
        </ul>
    </sec:authorize>


    <!-- ログイン中に表示するメニュー -->
    <sec:authorize access="isAuthenticated()">

        <!-- Left navbar links -->
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
            </li>
            <li class="nav-item d-none d-sm-inline-block">
                <a href="${f:h(pageContext.request.contextPath)}/" class="nav-link">Home</a>
            </li>
            <!-- <li class="nav-item d-none d-sm-inline-block">
            <a href="#" class="nav-link">Contact</a>
        </li> -->
        </ul>

        <!-- SEARCH FORM -->
        <!-- <form class="form-inline ml-3">
        <div class="input-group input-group-sm">
            <input class="form-control form-control-navbar" type="search" placeholder="Search" aria-label="Search">
            <div class="input-group-append">
                <button class="btn btn-navbar" type="submit">
                    <i class="fas fa-search"></i>
                </button>
            </div>
        </div>
    </form> -->

        <!-- Right navbar links -->
        <ul class="navbar-nav ml-auto">

            <sec:authorize access="hasRole('ROLE_PREVIOUS_ADMINISTRATOR')">
                <a class="nav-link blink text-danger"
                    href="${f:h(pageContext.request.contextPath)}/logout/impersonate">ユーザ切り替え中</a>
            </sec:authorize>

            
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <li class="nav-item">
                    <a class="nav-link" href="${f:h(pageContext.request.contextPath)}/admin/" role="button"><i class="fas fa-cogs"></i></a>
                </li>
            </sec:authorize>

            <!-- Notifications Dropdown Menu -->
            <li class="nav-item dropdown">
                <a class="nav-link" data-toggle="dropdown" href="#">
                    <i class="fas fa-th-large"></i>
                </a>
                <div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
                    <!-- <span class="dropdown-item dropdown-header"></span> -->

                    <a href="${f:h(pageContext.request.contextPath)}/account/" class="dropdown-item">
                        <i class="far fa-address-card mr-2"></i>マイアカウント情報
                    </a>

                    <a href="${f:h(pageContext.request.contextPath)}/job/summary" class="dropdown-item">
                        <i class="fas fa-history mr-2"></i>ジョブ実行結果
                    </a>                    

                    <div class="dropdown-divider"></div>
                    <form:form action="${f:h(pageContext.request.contextPath)}/logout" autocomplete="off">
                        <button id="logout" class="dropdown-item dropdown-footer">ログアウト</button>
                    </form:form>

                    <sec:authorize access="hasRole('ROLE_PREVIOUS_ADMINISTRATOR')">
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item dropdown-footer"
                            href="${f:h(pageContext.request.contextPath)}/logout/impersonate">ユーザ切替を中断</a>
                    </sec:authorize>

                </div>
            </li>
        </ul>

    </sec:authorize>

</nav>
<!-- /.navbar -->