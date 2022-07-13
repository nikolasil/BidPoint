import React from 'react';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import { Provider } from 'react-redux';
import axios from 'axios';
import store from './store';
import { CssBaseline } from '@mui/material';
import Landing from './components/pages/Landing/Landing';

const theme = createTheme({
  // palette: {
  // 	mode: 'dark',
  // }
});

function App() {
  axios.defaults.baseURL = 'http://localhost:8002/api/';

  return (
    <>
      <CssBaseline />
      <div className="App">
        <Provider store={store}>
          <ThemeProvider theme={theme}>
            <Landing />
          </ThemeProvider>
        </Provider>
      </div>
    </>
  );
}

export default App;
