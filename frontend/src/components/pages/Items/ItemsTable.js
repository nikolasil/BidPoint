import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { Button } from '@mui/material';
import { useDispatch } from 'react-redux';
import { approveUser } from '../../../actions/admin';

export default function UsersTable(props) {
  const rows = props.rows;
  const dispatch = useDispatch();
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Username</TableCell>
            <TableCell align="right">Firstname</TableCell>
            <TableCell align="right">Lastname</TableCell>
            <TableCell align="right">Approved</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((item) => (
            // <TableRow
            //   key={item.id}
            //   sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
            // >
            //   <TableCell component="th" scope="row">
            //     {item.username}
            //   </TableCell>
            //   <TableCell align="right">{item.firstname}</TableCell>
            //   <TableCell align="right">{item.lastname}</TableCell>
            //   <TableCell align="right">
            //     {item.approved ? (
            //       <Button variant="outlined" disabled color="success">
            //         Approve
            //       </Button>
            //     ) : (
            //       <Button
            //         variant="outlined"
            //         color="success"
            //         onClick={() => {
            //           dispatch(approveUser(item.username));
            //         }}
            //       >
            //         Approve
            //       </Button>
            //     )}
            //   </TableCell>
            // </TableRow>
            <h1>test</h1>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
