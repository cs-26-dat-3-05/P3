import { useQuery, useQueryClient } from "@tanstack/react-query";
import { casesApi, type Case } from "../api/cases";

export function useCase(caseId: number) {
  const queryClient = useQueryClient();

  const { data, isLoading, error } = useQuery({
    queryKey: ["cases", caseId],
    queryFn: () => casesApi.getById(caseId),
    // ensures id is a number
    enabled: Number.isFinite(caseId),
    // load from cache if case exist in cache
    initialData: () =>
      queryClient.getQueryData<Case[]>(["cases"])?.find((c) => c.id === caseId),
  });

  return { caseData: data, loading: isLoading, error };
}
