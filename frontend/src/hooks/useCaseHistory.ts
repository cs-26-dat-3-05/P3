import { queryOptions, useQuery } from "@tanstack/react-query";
import { casesApi } from "../api/cases";

export const caseHistoryQuery = (caseId: number) =>
  queryOptions({
    queryKey: ["cases", caseId, "history"],
    queryFn: () => casesApi.getHistory(caseId),
    enabled: Number.isFinite(caseId),
  });

export function useCaseHistory(caseId: number) {
  const { data, isLoading, error } = useQuery(caseHistoryQuery(caseId));
  return { history: data ?? [], loading: isLoading, error };
}
