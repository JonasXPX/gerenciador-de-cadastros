export type ICustomer = {
    id: any,
    name: string,
    surname: string,
    cpf: string,
    addresses: IAddress[]
}

export  type IAddress = {
    id: any
    address: string,
    uuid?: any
}