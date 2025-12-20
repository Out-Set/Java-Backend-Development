from opensearchpy import OpenSearch

client = OpenSearch(
    hosts=[{'host': 'localhost', 'port': 9200}],
    http_auth=('admin', '@qazxsW$345#'),
    use_ssl=True,
    verify_certs=False,
    ssl_show_warn=False
)

# Step 1: Get all active rules
rules = client.search(index="lab_rules", body={"query": {"term": {"active": True}}})

for rule in rules["hits"]["hits"]:
    r = rule["_source"]
    print(f"Applying rule: {r['id']}")
    query = {
        "script": {
            "source": f"""
              if (params.action == 'AUTO_APPROVE') {{
                ctx._source.auto_approved = true;
                ctx._source.status = 'APPROVED';
                ctx._source.approved_by = params.rule_id;
              }} else {{
                ctx._source.auto_approved = false;
                ctx._source.status = 'REVIEW';
              }}
            """,
            "params": {"action": r["action"], "rule_id": r["id"]}
        },
        "query": {
            "bool": {
                "must": [
                    {"term": {"test_code": r["test_code"]}},
                    {"script": {"script": {"source": r["expression"]}}}
                ]
            }
        }
    }
    client.update_by_query(index="lab_results", body=query, conflicts="proceed")
