/**
 * Organizer API – calls to /api/organizers.
 *
 * Usage in components: use the hook `useOrganizer(id)` from hooks/useOrganizer.ts.
 *
 * Interfaces must match the JSON returned by the backend DTOs.
 */
import { api } from "./client";

export interface BankAccount {
  id: number;
  registrationNumber: string;
  accountNumber: string;
  eventDefault: boolean;
  active: boolean;
}

export interface OrganizerEvent {
  id: number;
  name: string;
  bankAccountId: number;
}

export interface Organizer {
  id: number;
  name: string;
  email: string;
  phone: string;
  cvr: string;
  bankAccounts: BankAccount[];
  events: OrganizerEvent[];
}

export const organizerApi = {
  /** GET /api/organizers/{id} */
  getById: (id: number) => api.get<Organizer>(`/organizers/${id}`),
};
