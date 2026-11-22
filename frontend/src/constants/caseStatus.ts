import type { Case } from "../api/cases";

export const STATUS_MAP: Record<
  Case["status"],
  { step: number; label: string }
> = {
  REGISTERED: { step: 1, label: "Sag modtaget" },
  ORGANIZER_IDENTIFIED: { step: 2, label: "Arrangør identificeret" },
  BANK_DETAILS_REQUESTED: { step: 3, label: "Anmodet om kontooplysninger" },
  BANK_DETAILS_RECEIVED: { step: 4, label: "Kontooplysninger modtaget" },
  RETRANSFERRED: { step: 5, label: "Udbetaling gennemført" },
};
