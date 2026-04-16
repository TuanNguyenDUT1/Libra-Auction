export function TimeFormat(date: string) {
    const d = new Date(date);
    const time = new Intl.DateTimeFormat('vi-VN', {
        hour: '2-digit',
        minute: '2-digit',
        hour12: false,
    }).format(d);
    const offset = -d.getTimezoneOffset() / 60;
    const gmt = `GMT${offset >= 0 ? '+' : ''}${offset}`;
    return `${time} ${gmt}`;
}