import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableFooter from '@mui/material/TableFooter';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { TablePagination } from '@mui/material';
import { useDispatch } from 'react-redux';
import TableActions from '../../ui/TableActions';
import { useNavigate } from 'react-router-dom';

export default function UsersTable(props) {
  const {
    items,
    count,
    pageNumber,
    itemCount,
    sortField,
    sortDirection,
    handleChangePageNumber,
    handleChangeItemCount,
    handleChangeSortField,
    handleChangeSortDirection,
  } = props;

  const dispatch = useDispatch();
  const navigate = useNavigate();
  return (
    <TableContainer component={Paper} style={{ maxHeight: 'calc(73vh)' }}>
      <Table stickyHeader sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Id</TableCell>
            <TableCell align="right">Image</TableCell>
            <TableCell align="right">Name</TableCell>
            <TableCell align="right">Starting Price</TableCell>
            <TableCell align="right">Current Price</TableCell>
            <TableCell align="right">Buy Price</TableCell>
            <TableCell align="right">Number Of Bids</TableCell>
            <TableCell align="right">Category</TableCell>
            <TableCell align="right">Date Created</TableCell>
            <TableCell align="right">Date Updated</TableCell>
            <TableCell align="right">Date Ends</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {items.map((item) => (
            <TableRow
              onClick={() => navigate('/items/' + item.id)}
              key={item.id}
              sx={{
                '&:last-child td, &:last-child th': { border: 0 },
                cursor: 'pointer',
              }}
            >
              <TableCell component="th" scope="row">
                {item.id}
              </TableCell>
              <TableCell align="right">
                <img
                  style={{ width: '100px', height: '100px' }}
                  src="https://www.w3schools.com/w3css/img_snowtops.jpg"
                />
              </TableCell>
              <TableCell align="right">{item.name}</TableCell>
              <TableCell align="right">{item.startingPrice}</TableCell>
              <TableCell align="right">{item.currentPrice}</TableCell>
              <TableCell align="right">{item.buyPrice}</TableCell>
              <TableCell align="right">{item.numberOfBids}</TableCell>
              <TableCell align="right">{item.categories}</TableCell>
              <TableCell align="right">{item.dateCreated}</TableCell>
              <TableCell align="right">{item.dateUpdated}</TableCell>
              <TableCell align="right">{item.dateEnds}</TableCell>
            </TableRow>
          ))}
        </TableBody>
        <TableFooter>
          <TableRow>
            <TablePagination
              rowsPerPageOptions={[5, 10, 25, 50, 100]}
              count={count}
              rowsPerPage={itemCount}
              page={pageNumber}
              SelectProps={{
                inputProps: {
                  'aria-label': 'rows per page',
                },
                native: true,
              }}
              onPageChange={handleChangePageNumber}
              onRowsPerPageChange={handleChangeItemCount}
              ActionsComponent={TableActions}
            />
          </TableRow>
        </TableFooter>
      </Table>
    </TableContainer>
  );
}
