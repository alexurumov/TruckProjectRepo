const loader = {
    show: () => {
        $('#page-loader').show();
    },
    hide: () => {
        $('#page-loader').hide();
    },
};

const URLS = {
    managers: '/api/managers',
};

const toString = ({ id, name }) => {
    let columns = `
    <td>${name}</td>
    <td>
        <a href="/managers/remove/${id}" class="btn btn-block1 btn-sm btn-danger">Remove</a>
    </td>
`
    return `<tr>${columns}</tr>`
};

loader.show();
fetch(URLS.managers)
    .then(response => response.json())
    .then(managers => {
        let result = '';
        managers.forEach(manager => {
            const managerString = toString(manager);
            result += managerString;
        });

        $('#managers-table').html(result);
        loader.hide();
    });