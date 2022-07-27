import axios from 'axios';

const clearDefaultAuthHeader = () => {
  axios.defaults.headers.common['Authorization'] = '';
  console.log(
    'clearDefaultAuthHeader: Authorization = ',
    axios.defaults.headers.common['Authorization']
  );
};

export default clearDefaultAuthHeader;
