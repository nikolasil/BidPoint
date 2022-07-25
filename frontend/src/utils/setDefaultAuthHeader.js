import axios from 'axios';

const setDefaultAuthHeader = () => {
  if (localStorage.getItem('accessToken')) {
    axios.defaults.headers.common[
      'Authorization'
    ] = `Bearer ${localStorage.getItem('accessToken')}`;
    console.log(
      'setDefaultAuthHeader: Authorization = ',
      axios.defaults.headers.common['Authorization']
    );
  }
};

export default setDefaultAuthHeader;
