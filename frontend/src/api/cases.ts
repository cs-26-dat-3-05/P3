import { api } from "./client";

export interface Case {
  id: number;
  status:
    | "REGISTERED"
    | "ORGANIZER_IDENTIFIED"
    | "BANK_DETAILS_REQUESTED"
    | "BANK_DETAILS_RECEIVED"
    | "RETRANSFERRED";
  organizerId: string;
  organizerName: string | null;
  transactionId: number;
  eventId: string;
  rejectedRegistrationNumber: string;
  rejectedAccountNumber: string;
  amount: number;
  createdAt: string;
  requiresAction: boolean;
}

export const casesApi = {
  /** GET /api/cases */
  getAll: () => api.get<Case[]>("/cases"),
};
