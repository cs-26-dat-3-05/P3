/** 20. november */
export const formatDateMonth = (iso: string) => {
  return new Intl.DateTimeFormat("da-DK", {
    day: "numeric",
    month: "long",
  }).format(new Date(iso));
};

/** 18.06.2025 */
export const formatDate = (iso: string) =>
  new Intl.DateTimeFormat("da-DK", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
  }).format(new Date(iso));

/** 11.00 */
export const formatTime = (iso: string) =>
  new Intl.DateTimeFormat("da-DK", {
    hour: "2-digit",
    minute: "2-digit",
  }).format(new Date(iso));

/** DKK 48.520,00 */
export const formatAmount = (amount: number) =>
  `DKK ${new Intl.NumberFormat("da-DK", {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  }).format(amount)}`;
