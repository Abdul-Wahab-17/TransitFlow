function resetSelect(select, label) {
    select.innerHTML = "";
    const option = document.createElement("option");
    option.value = "";
    option.text = label;
    select.appendChild(option);
}

function fetchOptions(url, select, valueMapper, labelMapper) {
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error("Request failed");
            }
            return response.json();
        })
        .then(items => {
            items.forEach(item => {
                const option = document.createElement("option");
                option.value = valueMapper(item);
                option.text = labelMapper(item);
                select.appendChild(option);
            });
        })
        .catch(() => resetSelect(select, "No options available"));
}
