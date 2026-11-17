import { useEffect, useState } from "react";
import { casesApi, type Case } from "../api/cases";

export function useCases() {
  const [cases, setCases] = useState<Case[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    casesApi
      .getAll()
      .then((data) => setCases(data))
      .catch((err) => setError(err))
      .finally(() => setLoading(false));
  }, []);

  return { cases, loading, error };
}
