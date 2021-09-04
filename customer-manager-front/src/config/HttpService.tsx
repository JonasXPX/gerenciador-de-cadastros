import axios, { AxiosResponse } from "axios"

export type Pageable<T> = {
  totalPages?: number
  number?: number
  content: T[]
}

export default class HttpService<T> {
  async getAll(
    path: string,
    search: string = "",
    page: number = 0
  ): Promise<Pageable<T>> {
    const { data: receivedData } = await axios.get<Pageable<T>>(path, {
      params: { page, search, order: "id,desc", size: "10" },
    })
    return receivedData
  }

  async get(path: string): Promise<T> {
    const { data: receivedData } = await axios.get<T>(path)
    return receivedData
  }

  async post(path: string, data: T, params: any = {}): Promise<T> {
    try {
      const { data: receivedData } = await axios.post<T>(path, data, { params })
      return receivedData
    } catch (error) {
      throw this.handlerErrorMessaage(error)
    }
  }

  async postAny(path: string, data: any, params: any = {}): Promise<T> {
    try {
      const { data: receivedData } = await axios.post<T>(path, data, { params })
      return receivedData
    } catch (error) {
      throw this.handlerErrorMessaage(error)
    }
  }

  async put(path: string, data: T): Promise<T> {
    try {
      const { data: receivedData } = await axios.put<T>(path, data)
      return receivedData
    } catch (error) {
      throw this.handlerErrorMessaage(error)
    }
  }

  async delete(path: string): Promise<T> {
    const { data: receivedData } = await axios.delete(path)
    return receivedData
  }

  handlerErrorMessaage(error: any) {
    if (error.response.status === 400) {
      return error.response.data.errors
    }
    return error
  }
}
