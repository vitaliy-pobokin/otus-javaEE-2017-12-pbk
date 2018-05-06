<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet">
    <script>
        const ctx = "${pageContext.request.contextPath}";
        const url = ctx + "/api/credit_calc";
        function addEventListeners() {
            var inputs = document.getElementsByTagName("input");
            for(i = 0; i < inputs.length; i++) {
                inputs[i].onchange = onChangeHandler;
            }
            var selects = document.getElementsByTagName("select");
            for(i = 0; i < selects.length; i++) {
                selects[i].onchange = onChangeHandler;
            }
        }
        function onChangeHandler() {
            var table = document.getElementById("table");
            if (table.hidden !== true) {
                var tbody = document.getElementById("tbody");
                if (tbody !== null) {
                    table.removeChild(tbody);
                }
                table.setAttribute("hidden", true);
            }
        }
        function sendForm() {
            var sum = document.getElementById("sum").value;
            var percent = document.getElementById("percent").value;
            var period = document.getElementById("period").value;
            var date = document.getElementById("start_date").value;
            // var payment_type = document.getElementById("payment_type").value;
            // if (payment_type === "")
            var accept_header;
            if (document.getElementById("differential").selected === true) {
                accept_header = 'application/credit_payment-v1+json; charset=UTF-8';
            } else if (document.getElementById("annuitant").selected === true) {
                accept_header = 'application/credit_payment-v2+json; charset=UTF-8';
            }
            var data = 'sum='+sum+'&percent='+percent+'&period='+period+'&start_date='+date;

            postData(url, data, accept_header)
                .then(data => showData(data))
                .catch(error => console.error(error))
        }

        function postData(url, data, accept_header) {
            return fetch(url, {
                body: data,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Accept': accept_header
                },
                method: 'POST'
            })
                .then(response => response.json())
        }

        function showData(data) {
            var tbody = document.createElement("tbody");
            tbody.setAttribute("id", "tbody");
            data.forEach(function (item, index) {
                var tr = document.createElement("tr");
                var th = document.createElement("th");
                th.setAttribute('scope', 'row');
                tr.appendChild(th);
                var td_date = document.createElement("td");
                var td_payment = document.createElement("td");
                var td_debt_payment = document.createElement("td");
                var td_accrual_payment = document.createElement("td");
                var td_debt_balance = document.createElement("td");
                th.appendChild(document.createTextNode(index + 1));
                td_date.appendChild(document.createTextNode(item.payment_date));
                td_payment.appendChild(document.createTextNode(item.payment));
                td_debt_payment.appendChild(document.createTextNode(item.debt_payment));
                td_accrual_payment.appendChild(document.createTextNode(item.accrual_payment));
                td_debt_balance.appendChild(document.createTextNode(item.debt_balance));

                tr.appendChild(td_date);
                tr.appendChild(td_payment);
                tr.appendChild(td_debt_payment);
                tr.appendChild(td_accrual_payment);
                tr.appendChild(td_debt_balance);

                tbody.appendChild(tr);
            });
            var table = document.getElementById("table");
            table.removeAttribute('hidden');
            table.appendChild(tbody);
        }
        window.onload = addEventListeners;
    </script>
</head>
<body>
<div class="container">
    <form style="margin-top: 30px">
        <div class="row">
            <div class="form-group col-sm-6">
                <div class="row">
                    <label for="sum" class="col-sm-3 col-form-label">Сумма</label>
                    <div class="col-sm">
                        <input id="sum" name="sum" type="number" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-sm-6">
                <div class="row">
                    <label for="period" class="col-sm-3 col-form-label">Срок</label>
                    <div class="col-sm">
                        <input id="period" name="period" type="number" class="form-control">
                    </div>
                </div>

            </div>
            <div class="form-group col-sm-6">
                <div class="row">
                    <label for="start_date" class="col-sm-4 col-form-label">Дата получения</label>
                    <div class="col-sm">
                        <input id="start_date" name="start_date" type="date" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-sm-6">
                <div class="row">
                    <label for="percent" class="col-sm-3 col-form-label">Ставка</label>
                    <div class="col-sm">
                        <input id="percent" name="percent" type="number" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-sm-6">
                <div class="row">
                    <label for="payment_type" class="col-sm-3 col-form-label">Тип платежей</label>
                    <div class="col-sm">
                        <select id="payment_type" class="form-control">
                            <option id="differential">Дифференцированные</option>
                            <option id="annuitant">Аннуитентные</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <button class="btn btn-primary" onclick="sendForm()">Рассчитать</button>
    <div class="row">
        <table id="table" class="table table-hover table-responsive-sm" style="margin-top: 30px" hidden>
            <thead>
                <tr>
                    <th scope="col">№</th>
                    <th scope="col">Дата</th>
                    <th scope="col">Сумма</th>
                    <th scope="col">Погашение основного долга</th>
                    <th scope="col">Выплата процентов</th>
                    <th scope="col">Остаток</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
