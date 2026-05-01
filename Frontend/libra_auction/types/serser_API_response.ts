export interface ServerAPIResponse<T> {
    status: number,
    isSuccess: boolean,
    data?: T,
    errorMessage?: string
}