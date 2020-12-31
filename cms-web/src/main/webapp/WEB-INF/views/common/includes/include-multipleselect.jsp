<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/plugins/multiple-select/dist/multiple-select.min.css">
<script src="${pageContext.request.contextPath}/resources/plugins/multiple-select/dist/multiple-select.min.js"></script>

<script>
    $(document)
        .ready(
            function () {

                $(".multipleSelect").multipleSelect(
                    {filter: true}
                );

            });
</script>


<style type="text/css">
    .ms-choice {
        background-color: unset;
        border: none;
    }
    .ms-choice>span {
        padding-left: 4px;
    }
    .ms-drop {
        left: 0;
        border-radius: 2px;
    }
    .ms-drop input[type="radio"], .ms-drop input[type="checkbox"] {
        margin-top: .2rem;
    }
    .ms-search input {
        border-radius: 2px;
    }

    .ms-drop ul>li label {
        font-weight: normal !important;    
    }

    select.multipleSelect {
        visibility: hidden;
    }

</style>