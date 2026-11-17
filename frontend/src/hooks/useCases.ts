import { useQuery } from "@tanstack/react-query";
import { casesApi } from "../api/cases";

export function useCases() {
  const { data, isLoading, error } = useQuery({
    queryKey: ["cases"],
    queryFn: casesApi.getAll,
  });

  return { cases: data ?? [], loading: isLoading, error };
}
