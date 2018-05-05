<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script>
        const ctx = "${pageContext.request.contextPath}";
        const url = ctx + "/api/credit_calc";
        function addEventListeners() {
            var inputs = document.getElementsByTagName("input");
            for(i = 0; i < inputs.length; i++) {
                inputs[i].onchange = function () {
                    var table = document.getElementById("table");
                    if (table.hidden !== true) {
                        var tbody = document.getElementById("tbody");
                        if (tbody !== null) {
                            table.removeChild(tbody);
                        }
                        table.setAttribute("hidden", true);
                    }
                }
            }
        }
        function sendForm() {
            var sum = document.getElementById("sum").value;
            var percent = document.getElementById("percent").value;
            var period = document.getElementById("period").value;
            var date = document.getElementById("start_date").value;

            var data = 'sum='+sum+'&percent='+percent+'&period='+period+'&start_date='+date;

            postData(url, data)
                .then(data => showData(data))
                .catch(error => console.error(error))
        }

        function postData(url, data) {
            return fetch(url, {
                body: data,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Accept': 'application/credit_payment-v1+json; charset=UTF-8'
                },
                method: 'POST'
            })
                .then(response => response.json())
        }

        function showData(data) {
            var tbody = document.createElement("tbody");
            tbody.setAttribute("id", "tbody");
            data.forEach(function (item) {
                var tr = document.createElement("tr");
                var th = document.createElement("th");
                th.setAttribute('scope', 'row');
                tr.appendChild(th);
                var td_date = document.createElement("td");
                var td_payment = document.createElement("td");
                var td_debt_payment = document.createElement("td");
                var td_accrual_payment = document.createElement("td");
                var td_debt_balance = document.createElement("td");
                th.appendChild(document.createTextNode(item.month));
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
<form>
    <label for="sum">Sum</label>
    <input id="sum" name="sum" type="number">
    <label for="percent">Percent</label>
    <input id="percent" name="percent" type="number">
    <label for="period">Period</label>
    <input id="period" name="period" type="number">
    <label for="start_date">Start Date</label>
    <input id="start_date" name="start_date" type="date">
</form>
<button onclick="sendForm()">Calculate</button>
<table id="table" hidden>
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
</body>
</html>
