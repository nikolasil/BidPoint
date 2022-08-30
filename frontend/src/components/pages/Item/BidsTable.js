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

export default function BidsTable(props) {
  const {
    bids,
    count,
    pageNumber,
    itemCount,
    handleChangePageNumber,
    handleChangeItemCount,
  } = props;

  const dispatch = useDispatch();
  const navigate = useNavigate();
  return (
    <TableContainer component={Paper} style={{ maxHeight: '380px' }}>
      <Table stickyHeader sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Bid</TableCell>
            <TableCell align="right">Username</TableCell>
            <TableCell align="right">Date</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {bids &&
            bids.map((bid) => (
              <TableRow
                key={bid.amount}
                sx={{
                  '&:last-child td, &:last-child th': { border: 0 },
                  cursor: 'pointer',
                }}
              >
                <TableCell component="th" scope="row">
                  {bid.amount}
                </TableCell>
                <TableCell align="right">{bid.username}</TableCell>
                <TableCell align="right">{bid.dateCreated}</TableCell>
              </TableRow>
            ))}
        </TableBody>
        <TableFooter>
          <TableRow>
            <TablePagination
              rowsPerPageOptions={[5, 10, 25, 50]}
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
