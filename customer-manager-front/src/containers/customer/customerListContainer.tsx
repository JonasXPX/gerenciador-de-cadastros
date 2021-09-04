import { Button } from "@material-ui/core"
import { Fragment, useEffect, useState } from "react"
import { useHistory } from "react-router"
import { StringMappingType } from "typescript"
import Input from "../../components/Input"
import Table, { IColumnInfo } from "../../components/Table"
import HttpService, { Pageable } from "../../config/HttpService"
import AdvancedFilter from "./AdvancedFilterContainer"
import { ICustomer } from "./customerTypes"

type IFilter = {
  search?: string
  startDate?: any
  endDate?: any
}

const CustomerListContainer = (): JSX.Element => {
  const [data, setData] = useState<Pageable<ICustomer>>()
  const [filters, setFilters] = useState<IFilter | null>(null)

  const { push } = useHistory()

  useEffect(() => {
    getData()
  }, [])

  const getData = (filter = { search: "" }, page = 0) => {
    new HttpService<Pageable<ICustomer>>()
      .postAny("customer/advance-filter", filter, { page })
      .then(setData)
  }

  const onRowSelect = (item: ICustomer) => {
    push(`/customer/${item.id}/edit`)
  }

  const onAdd = () => {
    push(`/customer/new`)
  }

  const onPage = (filter: any, nextPage: number = 0) => {
    getData(filter, nextPage)
  }

  const clearFilters = () => setFilters({})

  return (
    <Fragment>
      <div>
        <Table
          data={data}
          columns={columns}
          onClick={onRowSelect}
          onAdd={onAdd}
          onPage={onPage}
          onSearch={getData}
          filters={filters}
          filterComponent={
            <AdvancedFilter
              filters={filters}
              setFilters={setFilters}
              clearFilters={clearFilters}
            />
          }
        />
      </div>
    </Fragment>
  )
}

const columns: IColumnInfo[] = [
  {
    label: "Cód.",
    name: "id",
  },
  {
    label: "Nome",
    name: "name",
  },
  {
    label: "Sobrenome",
    name: "surname",
  },
  {
    label: "CPF",
    name: "cpf",
  },
]

export default CustomerListContainer
