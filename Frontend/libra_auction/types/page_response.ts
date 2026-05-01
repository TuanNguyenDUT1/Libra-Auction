export interface PageResponse<T> {
    content: T[],
    totalPages: number,
    totalElements: number,
    currentPage: number,
    pageSize: number,
    isFirst: boolean,
    isLast: boolean
}