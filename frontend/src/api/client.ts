/**
 * Don't call fetch directly in components. Instead, create one file in src/api/
 * per backend entity (e.g. organizer.ts) with its types and API calls.
 * Paths are relative to BASE_URL, e.g. api.get<Organizer>("/organizers/1").
 */

const BASE_URL = "/api";

export class ApiError extends Error {
  status: number;
  body?: unknown;

  constructor(status: number, message: string, body?: unknown) {
    super(message);
    this.status = status;
    this.body = body;
  }
}

/** Sends a JSON request and returns the parsed response as T. */
async function request<T>(path: string, options: RequestInit = {}): Promise<T> {
  const res = await fetch(`${BASE_URL}${path}`, {
    ...options,
    headers: { "Content-Type": "application/json", ...options.headers },
  });

  if (!res.ok) {
    const body = await res.json().catch(() => undefined);
    throw new ApiError(res.status, body?.detail ?? res.statusText, body);
  }
  // 204 No Content has no body to parse
  return res.status === 204 ? (undefined as T) : res.json();
}

/** T = expected response type. `data` is serialized to JSON. */
export const api = {
  get: <T>(path: string) => request<T>(path),
  post: <T>(path: string, data: unknown) =>
    request<T>(path, { method: "POST", body: JSON.stringify(data) }),
  put: <T>(path: string, data: unknown) =>
    request<T>(path, { method: "PUT", body: JSON.stringify(data) }),
  delete: <T>(path: string) => request<T>(path, { method: "DELETE" }),
};
