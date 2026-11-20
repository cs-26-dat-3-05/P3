import { useEffect, useState } from "react";
import { organizerApi, type Organizer } from "../api/organizer";

export function useOrganizer(id: number) {
  const [organizer, setOrganizer] = useState<Organizer | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    organizerApi
      .getById(id)
      .then((data) => setOrganizer(data))
      .catch((err) => setError(err))
      .finally(() => setLoading(false));
  }, [id]);

  return { organizer, loading, error };
}
