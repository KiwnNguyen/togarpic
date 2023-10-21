document.addEventListener('DOMContentLoaded', function() {
	const selectedOption = document.querySelector('.selected-option');
	const selectedOption1 = document.querySelector('.selected-option1');
	const searchContainer = document.querySelector('.search-container');
	const searchInput = document.getElementById('searchInput');
	const optionsList = document.querySelector('.options-list');
	const options = Array.from(document.querySelectorAll('.option'));

	selectedOption.addEventListener('click', function() {
		optionsList.style.display = 'block';
		searchContainer.style.display = 'flex';
		searchInput.style.display = 'block';
		searchInput.focus();
	});

	searchInput.addEventListener('input', function(event) {
		const searchText = event.target.value.toLowerCase();

		options.forEach(option => {
			if (option.textContent.toLowerCase().includes(searchText)) {
				option.style.display = 'block';
			} else {
				option.style.display = 'none';
			}
		});
	});

	options.forEach(option => {
		option.addEventListener('click', function() {
			selectedOption.textContent = option.textContent;
			selectedOption.setAttribute('value', option.getAttribute('value')); // Thêm dòng này để thêm thuộc tính "value"


			selectedOption1.value = option.getAttribute('value'); // Hiển thị giá trị của thuộc tính 'value'


			optionsList.style.display = 'none';
			searchContainer.style.display = 'none';
			searchInput.style.display = 'none';
		});
	});

	document.addEventListener('click', function(event) {
		const target = event.target;
		if (!selectedOption.contains(target)) {
			optionsList.style.display = 'none';
			searchContainer.style.display = 'none';
			searchInput.style.display = 'none';
		}
	});
});