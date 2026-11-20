import { useQuery } from "@tanstack/react-query";
import { organizerApi } from "../api/organizer";

export function useOrganizer(organizerId: number) {
  const { data, isLoading, error } = useQuery({
    queryKey: ["organizers", organizerId],
    queryFn: () => organizerApi.getById(organizerId),
    enabled: Number.isFinite(organizerId),
    staleTime: 5 * 60_000,
  });

  return { organizer: data ?? null, loading: isLoading, error };
}
