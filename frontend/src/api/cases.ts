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

export interface CaseHistoryEntry {
  id: number;
  caseId: number;
  status: Case["status"];
  performedBy: string;
  createdAt: string;
}

export const casesApi = {
  /** GET /api/cases */
  getAll: () => api.get<Case[]>("/cases"),
  /** GET /api/cases/{id} */
  getById: (id: number) => api.get<Case>(`/cases/${id}`),
  /** GET /api/cases/{id}/history */
  getHistory: (id: number) =>
    api.get<CaseHistoryEntry[]>(`/cases/${id}/history`),
};
