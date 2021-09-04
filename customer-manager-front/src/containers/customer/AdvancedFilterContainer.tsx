import { Button } from "@material-ui/core"
import { Fragment } from "react"
import Input from "../../components/Input"

const AdvancedFilter = (props: any) => {
  return (
    <Fragment>
      <div style={{ padding: "20px", maxWidth: "12vw" }}>
        <span>Informe um periodo de inclusão</span>
        <Input
          error=""
          label=""
          value={props.filters?.startDate}
          onChange={(event: any) =>
            props.setFilters({
              ...props.filters,
              startDate: event.target.value,
            })
          }
          type="datetime-local"
        />

        <Input
          error=""
          label=""
          value={props.filters?.endDate}
          onChange={(event: any) =>
            props.setFilters({ ...props.filters, endDate: event.target.value })
          }
          type="datetime-local"
        />

        <Button onClick={props.clearFilters}>Limpar</Button>
      </div>
    </Fragment>
  )
}

export default AdvancedFilter
