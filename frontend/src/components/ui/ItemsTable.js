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
  Chip,
  CircularProgress,
  LinearProgress,
  TablePagination,
  TableSortLabel,
  TextField,
  Tooltip,
  Typography,
} from '@mui/material';
import { useDispatch } from 'react-redux';
import TableActions from './TableActions';
import { useNavigate } from 'react-router-dom';
import Carousel from './Carousel';
import moment from 'moment';
import { Box } from '@mui/system';
import ErrorOutlineOutlinedIcon from '@mui/icons-material/ErrorOutlineOutlined';
import BlockIcon from '@mui/icons-material/Block';
import { grey } from '@mui/material/colors';
export default function ItemsTable(props) {
  const {
    loading,
    fetched,
    items,
    count,
    pageNumber,
    itemCount,
    sortField,
    sortDirection,
    handleChangePageNumber,
    handleChangeItemCount,
    handleChangeSort,
    onClickCategoryChip,
  } = props;

  const dispatch = useDispatch();
  const navigate = useNavigate();
  return (
    <TableContainer component={Paper} style={{ maxHeight: 'calc(73vh)' }}>
      <Table stickyHeader sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell align="right">Image</TableCell>
            <TableCell align="right">
              <TableSortLabel
                active={sortField === 'name'}
                direction={sortDirection}
                onClick={() => handleChangeSort('name')}
              >
                Name
              </TableSortLabel>
            </TableCell>
            <TableCell align="right">
              <TableSortLabel
                active={sortField === 'startingPrice'}
                direction={sortDirection}
                onClick={() => handleChangeSort('startingPrice')}
              >
                Starting Price
              </TableSortLabel>
            </TableCell>
            <TableCell align="right">
              <TableSortLabel
                active={sortField === 'currentPrice'}
                direction={sortDirection}
                onClick={() => handleChangeSort('currentPrice')}
              >
                Current Price
              </TableSortLabel>
            </TableCell>
            <TableCell align="right">
              <TableSortLabel
                active={sortField === 'buyPrice'}
                direction={sortDirection}
                onClick={() => handleChangeSort('buyPrice')}
              >
                Buy Price
              </TableSortLabel>
            </TableCell>
            <TableCell align="right">
              <TableSortLabel
                active={sortField === 'numberOfBids'}
                direction={sortDirection}
                onClick={() => handleChangeSort('numberOfBids')}
              >
                Number Of Bids
              </TableSortLabel>
            </TableCell>
            <TableCell align="right">Category</TableCell>
            <TableCell align="right">
              <TableSortLabel
                active={sortField === 'user'}
                direction={sortDirection}
                onClick={() => handleChangeSort('user')}
              >
                Seller
              </TableSortLabel>
            </TableCell>
            <TableCell align="right">
              <TableSortLabel
                active={sortField === 'dateCreated'}
                direction={sortDirection}
                onClick={() => handleChangeSort('dateCreated')}
              >
                Date Created
              </TableSortLabel>
            </TableCell>
            <TableCell align="right">
              <TableSortLabel
                active={sortField === 'dateUpdated'}
                direction={sortDirection}
                onClick={() => handleChangeSort('dateUpdated')}
              >
                Date Updated
              </TableSortLabel>
            </TableCell>
            <TableCell align="right">
              <TableSortLabel
                active={sortField === 'dateEnds'}
                direction={sortDirection}
                onClick={() => handleChangeSort('dateEnds')}
              >
                Date Ends
              </TableSortLabel>
            </TableCell>
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
                <Typography>Couldn't fetch items</Typography>
              </TableCell>
            </TableRow>
          </TableBody>
        ) : count === 0 ? (
          <TableBody>
            <TableRow>
              <TableCell colSpan={11} align="center">
                <BlockIcon />
                <Typography>No items to display</Typography>
              </TableCell>
            </TableRow>
          </TableBody>
        ) : (
          <>
            <TableBody>
              {items.map((item) => (
                <Tooltip title={item.description}>
                  <TableRow
                    onClick={() => {
                      window.location.pathname === '/items'
                        ? navigate('/items/' + item.id)
                        : navigate('/account/items/edit/' + item.id);
                    }}
                    key={item.id}
                    sx={{
                      '&:last-child td, &:last-child th': { border: 0 },
                      cursor: 'pointer',
                      '&:hover': {
                        backgroundColor: '#EBEBEB',
                      },
                    }}
                  >
                    <TableCell align="right">
                      {item.images && (
                        <Carousel
                          height="100px"
                          width="100px"
                          images={item.images}
                          isBase64={true}
                        />
                      )}
                    </TableCell>

                    <TableCell align="right">{item.name}</TableCell>
                    <TableCell align="right">{item.startingPrice}</TableCell>
                    <TableCell align="right">{item.currentPrice}</TableCell>
                    <TableCell align="right">{item.buyPrice}</TableCell>
                    <TableCell align="right">{item.numberOfBids}</TableCell>
                    <TableCell align="right">
                      {item.categories.map((category) => (
                        <Tooltip title={'Toggle filter: ' + category}>
                          <Chip
                            sx={{
                              '&:hover': {
                                color: 'blue',
                                cursor: 'pointer',
                              },
                            }}
                            onClick={(event) =>
                              onClickCategoryChip(event, category)
                            }
                            key={category}
                            label={category}
                          />
                        </Tooltip>
                      ))}
                    </TableCell>
                    <TableCell align="right">{item.username}</TableCell>
                    <TableCell align="right">
                      {moment
                        .utc(item.dateCreated)
                        .local()
                        .format('DD/MM/YYYY HH:mm:ss')
                        .toString()}
                    </TableCell>
                    <TableCell align="right">
                      {moment
                        .utc(item.dateUpdated)
                        .local()
                        .format('DD/MM/YYYY HH:mm:ss')
                        .toString()}
                    </TableCell>
                    <TableCell align="right">
                      {moment
                        .utc(item.dateEnds)
                        .local()
                        .format('DD/MM/YYYY HH:mm:ss')
                        .toString()}
                    </TableCell>
                  </TableRow>
                </Tooltip>
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
