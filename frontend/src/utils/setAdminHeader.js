import axios from 'axios';

const setAdminHeader = () => {
	if (localStorage.getItem('admin-token')) {
		axios.defaults.headers.common['Authorization'] = `Bearer ${localStorage.getItem('admin-token')}`;
	}
};

export default setAdminHeader;