import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { notesApi } from "../api/notes";

export function useNotes(caseId: number) {
  const { data, isLoading, error } = useQuery({
    queryKey: ["cases", caseId, "notes"],
    queryFn: () => notesApi.getByCaseId(caseId),
    enabled: Number.isFinite(caseId),
    staleTime: 60_000,
  });

  return { notes: data ?? [], loading: isLoading, error };
}

export function useCreateNote(caseId: number) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (text: string) => notesApi.create(caseId, text),
    // fetch notes again to update components with the new note
    onSuccess: () =>
      queryClient.invalidateQueries({ queryKey: ["cases", caseId, "notes"] }),
  });
}
