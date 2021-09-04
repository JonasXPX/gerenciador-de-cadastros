import {
  CardContent,
  Grid,
  Card,
  CardActions,
  Button,
  Accordion,
  AccordionSummary,
  AccordionDetails,
  AccordionActions,
  FormControl,
} from "@material-ui/core"
import { Fragment, useEffect, useState } from "react"
import { useHistory, useParams } from "react-router"
import Input from "../../components/Input"
import HttpService from "../../config/HttpService"
import { ICustomer } from "./customerTypes"
import ExpandMoreIcon from "@material-ui/icons/ExpandMore"
import { createRandomId } from "../../config/fnhelper"

type ICustomerProps = {
  data?: ICustomer
}

const CustomerFormContainer = (props: ICustomerProps): JSX.Element => {
  const { id } = useParams<{ id?: string }>()
  const { push } = useHistory()

  const [customer, setCustomer] = useState<ICustomer>({
    id: null,
    addresses: [
      {
        id: null,
        address: "",
        uuid: createRandomId(),
      },
    ],
    cpf: "",
    name: "",
    surname: "",
  })

  const [errors, setErrors] = useState<any>([])

  useEffect(() => {
    if (id) {
      new HttpService<ICustomer>().get(`customer/${id}`).then(mapUUID)
    }
  }, [])

  const handlerPersist = () => (customer.id ? update() : save())

  const handlerDelete = () => {
    new HttpService().delete(`customer/${customer.id}`).then(back)
  }

  const save = () => {
    new HttpService<ICustomer>()
      .put("customer", customer)
      .then(back)
      .catch(setErrors)
  }

  const update = () => {
    new HttpService<ICustomer>()
      .post("customer", customer)
      .then(back)
      .catch(setErrors)
  }

  const back = () => push("/customer")

  const mapUUID = (customer: ICustomer) => {
    setCustomer({
      ...customer,
      addresses: customer.addresses.map((address) => ({
        ...address,
        uuid: createRandomId(),
      })),
    })
  }

  const onChangeField = (field: string) => (event: any) =>
    setCustomer({ ...customer, [field]: event.target.value })

  const onChangeAddress = (uuid: number) => (event: any) => {
    const index = getAddressIndex(uuid)
    const copy = [...customer.addresses]
    copy[index].address = event.target.value
    setCustomer({ ...customer, addresses: copy })
  }

  const removeAddress = (uuid: any) => {
    const index = getAddressIndex(uuid)
    const copy = [...customer.addresses]
    copy.splice(index, 1)

    setCustomer({
      ...customer,
      addresses: copy,
    })
  }

  const getAddressIndex = (uuid: any): number =>
    customer.addresses.findIndex((address) => address.uuid === uuid)

  const addNewAddress = () => {
    setCustomer({
      ...customer,
      addresses: [
        ...customer.addresses,
        {
          id: null,
          address: "",
          uuid: createRandomId(),
        },
      ],
    })
  }

  return (
    <Fragment>
      <Card>
        <CardContent style={{ margin: "auto 10vw" }}>
          <Grid container spacing={3}>
            <Grid item xs={12}>
              <Input
                error={errors["name"]}
                label={"Nome"}
                onChange={onChangeField("name")}
                value={customer.name}
              />
            </Grid>
            <Grid item xs={12}>
              <Input
                error={errors["surname"]}
                label={"Sobrenome"}
                onChange={onChangeField("surname")}
                value={customer.surname}
              />
            </Grid>
            <Grid item xs={12}>
              <Input
                error={errors["cpf"]}
                label={"CPF"}
                onChange={onChangeField("cpf")}
                value={customer.cpf}
              />
            </Grid>
            <Grid item xs={12}>
              <FormControl fullWidth error={true}>
                <Button
                  onClick={addNewAddress}
                  size="small"
                  variant="contained"
                  style={{ marginBottom: "5px" }}
                >
                  Adicionar
                </Button>

                {customer.addresses.map(({ id, address, uuid }) => (
                  <Accordion key={uuid} defaultExpanded={id === null}>
                    <AccordionSummary
                      expandIcon={<ExpandMoreIcon />}
                      id={`address-${uuid}`}
                    >
                      {address}
                    </AccordionSummary>
                    <AccordionDetails>
                      <Input
                        value={address}
                        label={"Endereço"}
                        error={errors["address"]}
                        onChange={onChangeAddress(uuid)}
                      />
                    </AccordionDetails>
                    {customer.addresses.length > 1 && (
                      <AccordionActions>
                        <Button
                          size="small"
                          onClick={() => removeAddress(uuid)}
                        >
                          Remover
                        </Button>
                      </AccordionActions>
                    )}
                  </Accordion>
                ))}
              </FormControl>
            </Grid>
          </Grid>
        </CardContent>
        <CardActions>
          <Button onClick={handlerPersist} variant="outlined">
            {id ? "Alterar" : "Salvar"}
          </Button>
          <Button onClick={back} variant="outlined">
            Cancelar
          </Button>
          {customer.id && (
            <Button variant="outlined" onClick={handlerDelete}>
              Remover
            </Button>
          )}
        </CardActions>
      </Card>
    </Fragment>
  )
}

export default CustomerFormContainer
