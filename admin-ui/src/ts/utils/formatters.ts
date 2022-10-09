/** Форматтер для булевых значений в таблице */
export const booleanFormatter = (row, column, cellValue) => {
    return cellValue ? "Да" : "Нет";
};
/** Форматтер в рублях */
export const rubFormatter = (row, column, cellValue) => {
    return new Intl.NumberFormat("ru-RU", { style: "currency", currency: "RUB" }).format(cellValue);
};