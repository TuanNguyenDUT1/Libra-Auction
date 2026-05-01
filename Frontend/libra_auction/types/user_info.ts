export interface UserInfo {
    id: string,
    hoVaTen: string,
    soDienThoai: string,
    CCCD: string,
    email: string,
    anhDaiDien: string,
    trangThaiEmail: "DA_XAC_THUC" | "CHUA_XAC_THUC" | "CHO_XAC_THUC",
    trangThaiTaiKhoan: "CHO_XAC_NHAN" | "HOAT_DONG" | "KHOA",
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