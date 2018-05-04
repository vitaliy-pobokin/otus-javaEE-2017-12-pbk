<html>
<head>
    <script>
        const ctx = "${pageContext.request.contextPath}";
        const url = ctx + "/api/credit_calc";
        function sendForm() {
            var sum = document.getElementById("sum").value;
            var percent = document.getElementById("percent").value;
            var period = document.getElementById("period").value;
            var date = document.getElementById("start_date").value;

            var data = 'sum='+sum+'&percent='+percent+'&period='+period+'&start_date='+date;

            postData(url, data)
                .then(data => console.log(data)) // JSON from `response.json()` call
                .catch(error => console.error(error))
        }

        function postData(url, data) {
            return fetch(url, {
                body: data,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Accept': 'application/credit_payment-v2+json'
                },
                method: 'POST'
            })
                .then(response => response.json()) // parses response to JSON
        }
    </script>
</head>
<body>
<form <%--action="${pageContext.request.contextPath}/api/credit_calc" method="post"--%>>
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
</body>
</html>
