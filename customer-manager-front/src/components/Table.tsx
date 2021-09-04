import { useEffect, useRef, useState } from "react"
import { Pageable } from "../config/HttpService"
import {
  Paper,
  TableBody,
  TableRow,
  Table as MaterialTable,
  TableCell,
  TableContainer,
  TableHead,
  TableFooter,
  Theme,
  Divider,
  IconButton,
  Popover,
} from "@material-ui/core"
import Fab from "@material-ui/core/Fab"
import { createStyles, makeStyles } from "@material-ui/styles"
import Input from "./Input"
import KeyboardArrowRight from "@material-ui/icons/KeyboardArrowRight"
import KeyboardArrowLeft from "@material-ui/icons/KeyboardArrowLeft"
import AddIcon from "@material-ui/icons/Add"

type ITableProps<T> = {
  onPage: Function
  onAdd: Function
  onClick: Function
  data: Pageable<T> | undefined
  columns: IColumnInfo[]
  onSearch: Function
  filterComponent?: React.ReactElement
  filters: any
}

export type IColumnInfo = {
  label: string
  name: string
}

const styled = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      marginLeft: "20px",
      marginTop: "10px",
      marginBottom: "10px",
    },
    tableInput: {
      display: "flex",
      justifyContent: "space-between",
    },
    page: {
      flexShrink: 0,
      marginLeft: "10%",
    },
  })
)

const Table = (props: ITableProps<any>): JSX.Element => {
  const classes = styled()

  const pageNumber: number = props.data?.number || 0
  const totalPage: number = props.data?.totalPages || 0

  const [search, setSearch] = useState<string>("")
  const [page, setPage] = useState<Number>(0)
  const [open, setOpen] = useState<HTMLButtonElement | null>(null)

  const onChangeSearch = (event: any) => setSearch(event.target.value)
  let timeout = useRef<any>()

  useEffect(() => {
    clearTimeout(timeout.current)

    timeout.current = setTimeout(() => {
      handlerSeach(search)
    }, 500)
  }, [search])

  const handlerSeach = (search: string) => {
    setPage(0)
    props.onSearch(buildFilter(search))
  }

  const buildFilter = (search: string) => ({ ...props.filters, search, page })

  const handlerNextPage = () => {
    props.onPage(buildFilter(search), pageNumber + 1)
  }

  const handlerFowardPage = () => {
    props.onPage(buildFilter(search), pageNumber - 1)
  }

  const openFilter = (event: React.MouseEvent<HTMLButtonElement>) => {
    setOpen(event.currentTarget)
  }

  const handleClose = () => {
    setOpen(null)
  }

  return (
    <TableContainer component={Paper}>
      <Popover open={!!open} anchorEl={open} onClose={handleClose}>
        {props.filterComponent}
      </Popover>
      <MaterialTable>
        <TableHead>
          <TableRow>
            <TableCell colSpan={props.columns.length}>
              <div className={classes.tableInput}>
                <Input
                  onChange={onChangeSearch}
                  value={search}
                  error={""}
                  label={"Buscar registros"}
                  showFilter
                  onClickFilter={openFilter}
                />
                <div>
                  <Divider orientation="vertical" variant="middle" />
                </div>
                <Fab size="medium" onClick={() => props.onAdd()}>
                  <AddIcon />
                </Fab>
              </div>
            </TableCell>
          </TableRow>
          <TableRow>
            {props.columns.map((column) => (
              <TableCell key={column.name}>{column.label}</TableCell>
            ))}
          </TableRow>
        </TableHead>

        <TableBody>
          {props.data?.content.map((item) => (
            <TableRow hover key={item.id} onClick={() => props.onClick(item)}>
              {props.columns.map((column) => (
                <TableCell>{item[column.name]}</TableCell>
              ))}
            </TableRow>
          ))}
        </TableBody>

        <TableFooter>
          <TableRow>
            <div className={classes.page}>
              <IconButton
                onClick={handlerFowardPage}
                disabled={pageNumber <= 0}
              >
                <KeyboardArrowLeft />
              </IconButton>
              {pageNumber + 1}-{totalPage}
              <IconButton
                onClick={handlerNextPage}
                disabled={pageNumber + 1 >= totalPage}
              >
                <KeyboardArrowRight />
              </IconButton>
            </div>
          </TableRow>
        </TableFooter>
      </MaterialTable>
    </TableContainer>
  )
}

export default Table
