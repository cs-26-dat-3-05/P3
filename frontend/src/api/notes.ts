import { api } from "./client";

export interface Note {
  id: number;
  caseId: number;
  text: string;
  createdBy: string;
  createdAt: string;
}

export const notesApi = {
  /** GET /api/cases/{id}/notes */
  getByCaseId: (caseId: number) => api.get<Note[]>(`/cases/${caseId}/notes`),

  /** POST /api/cases/{id}/notes */
  create: (caseId: number, text: string) =>
    api.post<Note>(`/cases/${caseId}/notes`, { text }),
};
