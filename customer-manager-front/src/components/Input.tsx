import {
  InputAdornment,
  TextField,
  IconButton,
  Tooltip,
} from "@material-ui/core"
import { ChangeEventHandler } from "react"
import FilterListIcon from "@material-ui/icons/FilterList"

type IInputProps = {
  label: string
  error: string
  value: any
  onChange: ChangeEventHandler
  onClickFilter?: Function
  showFilter?: boolean
  type?: string | undefined
}

const Input = (props: IInputProps) => {
  return (
    <TextField
      fullWidth
      label={props.label}
      type={props.type || undefined}
      onChange={props.onChange}
      value={props.value}
      helperText={props.error}
      error={!!props.error}
      variant="outlined"
      InputProps={{
        endAdornment: props.showFilter && (
          <InputAdornment position="end">
            <Tooltip title="Filtro avançado">
              <IconButton
                onClick={(event: any) =>
                  props.onClickFilter && props.onClickFilter(event)
                }
              >
                <FilterListIcon />
              </IconButton>
            </Tooltip>
          </InputAdornment>
        ),
      }}
    />
  )
}

export default Input
