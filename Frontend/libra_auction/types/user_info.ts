import { TrangThaiEmail, TrangThaiTaiKhoan } from "./status"

export interface UserInfo {
    id: string,
    hoVaTen: string,
    soDienThoai: string,
    CCCD: string,
    email: string,
    anhDaiDien: string,
    trangThaiEmail: TrangThaiEmail,
    trangThaiTaiKhoan: TrangThaiTaiKhoan,
    roles: Role[]
}

export interface Role {
    name: string,
    description: string,
    permissions: Permission[]
}

export interface Permission {
    name: string,
    description: string,
}