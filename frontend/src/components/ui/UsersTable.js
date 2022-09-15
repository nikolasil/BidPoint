import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableFooter from '@mui/material/TableFooter';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import {
  Button,
  Chip,
  CircularProgress,
  LinearProgress,
  TablePagination,
  TableSortLabel,
  Tooltip,
  Typography,
} from '@mui/material';
import { useDispatch } from 'react-redux';
import TableActions from './TableActions';
import { useNavigate } from 'react-router-dom';
import ErrorOutlineOutlinedIcon from '@mui/icons-material/ErrorOutlineOutlined';
import BlockIcon from '@mui/icons-material/Block';
import { approveUser } from '../../actions/admin';

export default function UsersTable(props) {
  const {
    loading,
    fetched,
    users,
    count,
    pageNumber,
    itemCount,
    sortField,
    sortDirection,
    handleChangePageNumber,
    handleChangeItemCount,
    handleChangeSort,
  } = props;

  const dispatch = useDispatch();
  const navigate = useNavigate();
  return (
    <TableContainer component={Paper} style={{ maxHeight: 'calc(73vh)' }}>
      <Table stickyHeader sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell align="right">
              <TableSortLabel
                active={sortField === 'username'}
                direction={sortDirection}
                onClick={() => handleChangeSort('username')}
              >
                Username
              </TableSortLabel>
            </TableCell>
            <TableCell align="right">
              <TableSortLabel
                active={sortField === 'firstname'}
                direction={sortDirection}
                onClick={() => handleChangeSort('firstname')}
              >
                Firstname
              </TableSortLabel>
            </TableCell>
            <TableCell align="right">
              <TableSortLabel
                active={sortField === 'lastname'}
                direction={sortDirection}
                onClick={() => handleChangeSort('lastname')}
              >
                Lastname
              </TableSortLabel>
            </TableCell>
            <TableCell align="right">Approved</TableCell>
          </TableRow>
        </TableHead>
        {loading ? (
          <TableBody>
            <TableRow>
              <TableCell colSpan={11}>
                <LinearProgress />
              </TableCell>
            </TableRow>
          </TableBody>
        ) : !fetched ? (
          <TableBody>
            <TableRow>
              <TableCell colSpan={11} align="center">
                <ErrorOutlineOutlinedIcon />
                <Typography>Couldn't fetch users</Typography>
              </TableCell>
            </TableRow>
          </TableBody>
        ) : count == 0 ? (
          <TableBody>
            <TableRow>
              <TableCell colSpan={11} align="center">
                <BlockIcon />
                <Typography>No users to display</Typography>
              </TableCell>
            </TableRow>
          </TableBody>
        ) : (
          <>
            <TableBody>
              {users.map((user) => (
                <TableRow
                  onClick={() => {
                    navigate('/admin/users/' + user.username);
                  }}
                  key={user.username}
                  sx={{
                    '&:last-child td, &:last-child th': { border: 0 },
                    cursor: 'pointer',
                  }}
                >
                  <TableCell component="th" scope="row">
                    {user.username}
                  </TableCell>
                  <TableCell align="right">{user.firstname}</TableCell>
                  <TableCell align="right">{user.lastname}</TableCell>

                  <TableCell align="right">
                    {user.approved ? (
                      <Button variant="outlined" disabled color="success">
                        Approve
                      </Button>
                    ) : (
                      <Button
                        variant="outlined"
                        color="success"
                        onClick={(event) => {
                          event.stopPropagation();
                          dispatch(approveUser(user.username));
                        }}
                      >
                        Approve
                      </Button>
                    )}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </>
        )}
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
