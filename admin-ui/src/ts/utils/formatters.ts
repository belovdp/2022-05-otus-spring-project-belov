/** Форматтер для булевых значений в таблице */
export const booleanFormatter = (row, column, cellValue) => {
    return cellValue ? "Да" : "Нет";
};